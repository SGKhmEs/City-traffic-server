import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CarOnRoute } from './car-on-route.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CarOnRouteService {

    private resourceUrl = 'api/car-on-routes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(carOnRoute: CarOnRoute): Observable<CarOnRoute> {
        const copy = this.convert(carOnRoute);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(carOnRoute: CarOnRoute): Observable<CarOnRoute> {
        const copy = this.convert(carOnRoute);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CarOnRoute> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.timeLogin = this.dateUtils
            .convertLocalDateFromServer(entity.timeLogin);
        entity.timeLogout = this.dateUtils
            .convertLocalDateFromServer(entity.timeLogout);
    }

    private convert(carOnRoute: CarOnRoute): CarOnRoute {
        const copy: CarOnRoute = Object.assign({}, carOnRoute);
        copy.timeLogin = this.dateUtils
            .convertLocalDateToServer(carOnRoute.timeLogin);
        copy.timeLogout = this.dateUtils
            .convertLocalDateToServer(carOnRoute.timeLogout);
        return copy;
    }
}
