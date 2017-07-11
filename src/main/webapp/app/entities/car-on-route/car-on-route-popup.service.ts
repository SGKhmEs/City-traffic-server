import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CarOnRoute } from './car-on-route.model';
import { CarOnRouteService } from './car-on-route.service';

@Injectable()
export class CarOnRoutePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private carOnRouteService: CarOnRouteService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.carOnRouteService.find(id).subscribe((carOnRoute) => {
                if (carOnRoute.timeLogin) {
                    carOnRoute.timeLogin = {
                        year: carOnRoute.timeLogin.getFullYear(),
                        month: carOnRoute.timeLogin.getMonth() + 1,
                        day: carOnRoute.timeLogin.getDate()
                    };
                }
                if (carOnRoute.timeLogout) {
                    carOnRoute.timeLogout = {
                        year: carOnRoute.timeLogout.getFullYear(),
                        month: carOnRoute.timeLogout.getMonth() + 1,
                        day: carOnRoute.timeLogout.getDate()
                    };
                }
                this.carOnRouteModalRef(component, carOnRoute);
            });
        } else {
            return this.carOnRouteModalRef(component, new CarOnRoute());
        }
    }

    carOnRouteModalRef(component: Component, carOnRoute: CarOnRoute): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.carOnRoute = carOnRoute;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
