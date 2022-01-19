import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Component } from '@angular/core';

export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:8080/';
    frontendEndpoint: string = '/restaurant/';
    stompClient: any;
    // component: Component;
    handleNotification: Function;
    backendString : string;
    frontString : string;


    constructor(backend : string, frontend : string, handler : Function){
        this.backendString = backend;
        this.frontString = frontend
        this.webSocketEndPoint += backend;
        this.frontendEndpoint += frontend;
        this.handleNotification = handler;
    }

    // initialize(backend : string, frontend : string, handler : Function){
        
    // }

    _connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame: any) {
            _this.stompClient.subscribe(_this.frontendEndpoint, function (sdkEvent: any) {
                console.log("Message Recieved from Server :: " + sdkEvent);
                _this.handleNotification(sdkEvent);
            });
            //_this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error: string) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

 /**
  * Send message to sever via web socket
  * @param {*} func
  */
    _send(endpoint: string, obj: any, code: number) {
        this.stompClient.send(endpoint, {}, obj, code);
    }
}