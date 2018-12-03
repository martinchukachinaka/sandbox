import { CanDeactivate, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

export interface ComponentCanDeactivate {
    canDeactivate: () => Promise<boolean> | Observable<boolean> | boolean;
}

@Injectable({ providedIn: 'root' })
export class UnsavedChangesGuard implements CanDeactivate<ComponentCanDeactivate> {
    canDeactivate(
        component: ComponentCanDeactivate,
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot,
        next?: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return component.canDeactivate();
    }
}
