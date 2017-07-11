import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CarOnRoute } from './car-on-route.model';
import { CarOnRoutePopupService } from './car-on-route-popup.service';
import { CarOnRouteService } from './car-on-route.service';
import { Car, CarService } from '../car';
import { BusRoute, BusRouteService } from '../bus-route';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-car-on-route-dialog',
    templateUrl: './car-on-route-dialog.component.html'
})
export class CarOnRouteDialogComponent implements OnInit {

    carOnRoute: CarOnRoute;
    authorities: any[];
    isSaving: boolean;

    cars: Car[];

    busroutes: BusRoute[];
    timeLoginDp: any;
    timeLogoutDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private carOnRouteService: CarOnRouteService,
        private carService: CarService,
        private busRouteService: BusRouteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.carService.query()
            .subscribe((res: ResponseWrapper) => { this.cars = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.busRouteService.query()
            .subscribe((res: ResponseWrapper) => { this.busroutes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.carOnRoute.id !== undefined) {
            this.subscribeToSaveResponse(
                this.carOnRouteService.update(this.carOnRoute), false);
        } else {
            this.subscribeToSaveResponse(
                this.carOnRouteService.create(this.carOnRoute), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CarOnRoute>, isCreated: boolean) {
        result.subscribe((res: CarOnRoute) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CarOnRoute, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'cityTrafficServerApp.carOnRoute.created'
            : 'cityTrafficServerApp.carOnRoute.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'carOnRouteListModification', content: 'OK'});
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

    trackBusRouteById(index: number, item: BusRoute) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-car-on-route-popup',
    template: ''
})
export class CarOnRoutePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private carOnRoutePopupService: CarOnRoutePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.carOnRoutePopupService
                    .open(CarOnRouteDialogComponent, params['id']);
            } else {
                this.modalRef = this.carOnRoutePopupService
                    .open(CarOnRouteDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
