import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { CarOnRoute } from './car-on-route.model';
import { CarOnRoutePopupService } from './car-on-route-popup.service';
import { CarOnRouteService } from './car-on-route.service';

@Component({
    selector: 'jhi-car-on-route-delete-dialog',
    templateUrl: './car-on-route-delete-dialog.component.html'
})
export class CarOnRouteDeleteDialogComponent {

    carOnRoute: CarOnRoute;

    constructor(
        private carOnRouteService: CarOnRouteService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.carOnRouteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'carOnRouteListModification',
                content: 'Deleted an carOnRoute'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cityTrafficServerApp.carOnRoute.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-car-on-route-delete-popup',
    template: ''
})
export class CarOnRouteDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private carOnRoutePopupService: CarOnRoutePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.carOnRoutePopupService
                .open(CarOnRouteDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
