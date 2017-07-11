import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { GPS } from './gps.model';
import { GPSPopupService } from './gps-popup.service';
import { GPSService } from './gps.service';

@Component({
    selector: 'jhi-gps-delete-dialog',
    templateUrl: './gps-delete-dialog.component.html'
})
export class GPSDeleteDialogComponent {

    gPS: GPS;

    constructor(
        private gPSService: GPSService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gPSService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gPSListModification',
                content: 'Deleted an gPS'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('cityTrafficServerApp.gPS.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-gps-delete-popup',
    template: ''
})
export class GPSDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gPSPopupService: GPSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.gPSPopupService
                .open(GPSDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
