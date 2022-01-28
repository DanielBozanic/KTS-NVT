import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router } from "@angular/router";
import { Globals } from "../root/globals";

@Injectable()
export class WaiterGuardService implements CanActivate {
    constructor(
        public router: Router,
        private globals: Globals,
    ) { }

    canActivate(route: ActivatedRouteSnapshot): boolean {
        if (this.globals.called) {
            this.globals.called = false;
            return true;
        }
        return false;
    }
}