import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CabPositionComponent } from './cab-position.component';
import { CabPositionDetailComponent } from './cab-position-detail.component';
import { CabPositionPopupComponent } from './cab-position-dialog.component';
import { CabPositionDeletePopupComponent } from './cab-position-delete-dialog.component';

import { Principal } from '../../shared';

export const cabPositionRoute: Routes = [
  {
    path: 'cab-position',
    component: CabPositionComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CabPositions'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'cab-position/:id',
    component: CabPositionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CabPositions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cabPositionPopupRoute: Routes = [
  {
    path: 'cab-position-new',
    component: CabPositionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CabPositions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'cab-position/:id/edit',
    component: CabPositionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CabPositions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'cab-position/:id/delete',
    component: CabPositionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'CabPositions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
