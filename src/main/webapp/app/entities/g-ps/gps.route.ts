import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GPSComponent } from './gps.component';
import { GPSDetailComponent } from './gps-detail.component';
import { GPSPopupComponent } from './gps-dialog.component';
import { GPSDeletePopupComponent } from './gps-delete-dialog.component';

import { Principal } from '../../shared';

export const gPSRoute: Routes = [
    {
        path: 'gps',
        component: GPSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.gPS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gps/:id',
        component: GPSDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.gPS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gPSPopupRoute: Routes = [
    {
        path: 'gps-new',
        component: GPSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.gPS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gps/:id/edit',
        component: GPSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.gPS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gps/:id/delete',
        component: GPSDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.gPS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
