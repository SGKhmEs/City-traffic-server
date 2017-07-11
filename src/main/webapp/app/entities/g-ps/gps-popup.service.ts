import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GPS } from './gps.model';
import { GPSService } from './gps.service';

@Injectable()
export class GPSPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private gPSService: GPSService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.gPSService.find(id).subscribe((gPS) => {
                if (gPS.time) {
                    gPS.time = {
                        year: gPS.time.getFullYear(),
                        month: gPS.time.getMonth() + 1,
                        day: gPS.time.getDate()
                    };
                }
                this.gPSModalRef(component, gPS);
            });
        } else {
            return this.gPSModalRef(component, new GPS());
        }
    }

    gPSModalRef(component: Component, gPS: GPS): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.gPS = gPS;
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
