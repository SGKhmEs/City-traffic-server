import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CabPosition } from './cab-position.model';
import { CabPositionPopupService } from './cab-position-popup.service';
import { CabPositionService } from './cab-position.service';

@Component({
    selector: 'jhi-cab-position-dialog',
    templateUrl: './cab-position-dialog.component.html'
})
export class CabPositionDialogComponent implements OnInit {

    cabPosition: CabPosition;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cabPositionService: CabPositionService,
        private eventManager: EventManager
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
        if (this.cabPosition.id !== undefined) {
            this.cabPositionService.update(this.cabPosition)
                .subscribe((res: CabPosition) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.cabPositionService.create(this.cabPosition)
                .subscribe((res: CabPosition) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CabPosition) {
        this.eventManager.broadcast({ name: 'cabPositionListModification', content: 'OK'});
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
    selector: 'jhi-cab-position-popup',
    template: ''
})
export class CabPositionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cabPositionPopupService: CabPositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.cabPositionPopupService
                    .open(CabPositionDialogComponent, params['id']);
            } else {
                this.modalRef = this.cabPositionPopupService
                    .open(CabPositionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
