import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Component } from '@angular/core';

export class WebSocketAPI {
    private webSocketEndPoint: string = 'http://localhost:8080/app/notification';
    private frontendEndpoint: string = '/restaurant/';
    private stompClient: any;
    // component: Component;
    private handleNotification: Function;


    constructor(frontend : string, handler : Function){
        this.frontendEndpoint += frontend;
        this.handleNotification = handler;
    }

    // initialize(backend : string, frontend : string, handler : Function){
        
    // }

    _connect() {
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

    // on error, schedule a reconnection attempt
    // errorCallBack(error: string) {
    //     console.log("errorCallBack -> " + error)
    //     setTimeout(() => {
    //         this._connect();
    //     }, 5000);
    // }

 /**
  * Send message to sever via web socket
  * @param {*} func
  */
    _send(endpoint: string, obj: any) {
        this.stompClient.send('/app/' + endpoint, {}, JSON.stringify(obj));
    }
    
}