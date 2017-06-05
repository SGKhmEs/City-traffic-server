import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { CabPosition } from './cab-position.model';
import { CabPositionService } from './cab-position.service';

@Component({
    selector: 'jhi-cab-position-detail',
    templateUrl: './cab-position-detail.component.html'
})
export class CabPositionDetailComponent implements OnInit, OnDestroy {

    cabPosition: CabPosition;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private cabPositionService: CabPositionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCabPositions();
    }

    load(id) {
        this.cabPositionService.find(id).subscribe((cabPosition) => {
            this.cabPosition = cabPosition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCabPositions() {
        this.eventSubscriber = this.eventManager.subscribe('cabPositionListModification', (response) => this.load(this.cabPosition.id));
    }
}
