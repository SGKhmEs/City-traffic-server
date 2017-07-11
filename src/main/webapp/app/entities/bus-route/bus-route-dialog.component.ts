import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BusRoute } from './bus-route.model';
import { BusRoutePopupService } from './bus-route-popup.service';
import { BusRouteService } from './bus-route.service';

@Component({
    selector: 'jhi-bus-route-dialog',
    templateUrl: './bus-route-dialog.component.html'
})
export class BusRouteDialogComponent implements OnInit {

    busRoute: BusRoute;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private busRouteService: BusRouteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.busRoute.id !== undefined) {
            this.subscribeToSaveResponse(
                this.busRouteService.update(this.busRoute), false);
        } else {
            this.subscribeToSaveResponse(
                this.busRouteService.create(this.busRoute), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BusRoute>, isCreated: boolean) {
        result.subscribe((res: BusRoute) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BusRoute, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cityTrafficServerApp.busRoute.created'
            : 'cityTrafficServerApp.busRoute.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'busRouteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-bus-route-popup',
    template: ''
})
export class BusRoutePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private busRoutePopupService: BusRoutePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.busRoutePopupService
                    .open(BusRouteDialogComponent, params['id']);
            } else {
                this.modalRef = this.busRoutePopupService
                    .open(BusRouteDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
