import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Component, Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class WebSocketAPI {
    private webSocketEndPoint: string = 'http://localhost:8080/app/notification';
    private frontendEndpoint: string = '/restaurant/';
    private stompClient: any;
    // component: Component;
    private handleNotification: Function = () => {};

    _connect(frontend : string, handler : Function) {
        this.frontendEndpoint += frontend;
        this.handleNotification = handler;

        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({}, () => {
            this.stompClient.subscribe(this.frontendEndpoint, (data : any) => {
                console.log("Message Recieved from Server :: " + data);
                this.handleNotification(JSON.parse(data.body));
            },
            (error : any) =>{
                console.log('Subscribe: error:' + error)
            });
            //_this.stompClient.reconnect_delay = 2000;
        });
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

 /**
  * Send message to sever via web socket
  * @param {*} func
  */
    _send(endpoint: string, obj: any) {
        this.stompClient.send('/app/' + endpoint, {}, JSON.stringify(obj));
    }
    
}