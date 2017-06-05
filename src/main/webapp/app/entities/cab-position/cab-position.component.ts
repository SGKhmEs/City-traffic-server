import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { CabPosition } from './cab-position.model';
import { CabPositionService } from './cab-position.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-cab-position',
    templateUrl: './cab-position.component.html'
})
export class CabPositionComponent implements OnInit, OnDestroy {
cabPositions: CabPosition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cabPositionService: CabPositionService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cabPositionService.query().subscribe(
            (res: Response) => {
                this.cabPositions = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCabPositions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CabPosition) {
        return item.id;
    }
    registerChangeInCabPositions() {
        this.eventSubscriber = this.eventManager.subscribe('cabPositionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
