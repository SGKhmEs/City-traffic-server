import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GPS } from './gps.model';
import { GPSPopupService } from './gps-popup.service';
import { GPSService } from './gps.service';
import { Car, CarService } from '../car';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-gps-dialog',
    templateUrl: './gps-dialog.component.html'
})
export class GPSDialogComponent implements OnInit {

    gPS: GPS;
    authorities: any[];
    isSaving: boolean;

    cars: Car[];
    timeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private gPSService: GPSService,
        private carService: CarService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.carService.query()
            .subscribe((res: ResponseWrapper) => { this.cars = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.gPS.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gPSService.update(this.gPS), false);
        } else {
            this.subscribeToSaveResponse(
                this.gPSService.create(this.gPS), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<GPS>, isCreated: boolean) {
        result.subscribe((res: GPS) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: GPS, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cityTrafficServerApp.gPS.created'
            : 'cityTrafficServerApp.gPS.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'gPSListModification', content: 'OK'});
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

    trackCarById(index: number, item: Car) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-gps-popup',
    template: ''
})
export class GPSPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gPSPopupService: GPSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.gPSPopupService
                    .open(GPSDialogComponent, params['id']);
            } else {
                this.modalRef = this.gPSPopupService
                    .open(GPSDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
