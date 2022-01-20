import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class Globals {
    cookNotifications: number = 0;
    waiterNotifications: number = 0;
    bartenderNotifications: number = 0;
}