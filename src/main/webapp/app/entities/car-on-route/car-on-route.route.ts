import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CarOnRouteComponent } from './car-on-route.component';
import { CarOnRouteDetailComponent } from './car-on-route-detail.component';
import { CarOnRoutePopupComponent } from './car-on-route-dialog.component';
import { CarOnRouteDeletePopupComponent } from './car-on-route-delete-dialog.component';

import { Principal } from '../../shared';

export const carOnRouteRoute: Routes = [
    {
        path: 'car-on-route',
        component: CarOnRouteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.carOnRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'car-on-route/:id',
        component: CarOnRouteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.carOnRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const carOnRoutePopupRoute: Routes = [
    {
        path: 'car-on-route-new',
        component: CarOnRoutePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.carOnRoute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'car-on-route/:id/edit',
        component: CarOnRoutePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.carOnRoute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'car-on-route/:id/delete',
        component: CarOnRouteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cityTrafficServerApp.carOnRoute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
