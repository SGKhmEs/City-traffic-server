import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CabPosition } from './cab-position.model';
import { CabPositionPopupService } from './cab-position-popup.service';
import { CabPositionService } from './cab-position.service';

@Component({
    selector: 'jhi-cab-position-delete-dialog',
    templateUrl: './cab-position-delete-dialog.component.html'
})
export class CabPositionDeleteDialogComponent {

    cabPosition: CabPosition;

    constructor(
        private cabPositionService: CabPositionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cabPositionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cabPositionListModification',
                content: 'Deleted an cabPosition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cab-position-delete-popup',
    template: ''
})
export class CabPositionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cabPositionPopupService: CabPositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.cabPositionPopupService
                .open(CabPositionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
