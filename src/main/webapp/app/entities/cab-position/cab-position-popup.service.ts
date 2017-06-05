import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CabPosition } from './cab-position.model';
import { CabPositionService } from './cab-position.service';
@Injectable()
export class CabPositionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cabPositionService: CabPositionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.cabPositionService.find(id).subscribe((cabPosition) => {
                this.cabPositionModalRef(component, cabPosition);
            });
        } else {
            return this.cabPositionModalRef(component, new CabPosition());
        }
    }

    cabPositionModalRef(component: Component, cabPosition: CabPosition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cabPosition = cabPosition;
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
