import{WS_ADRESS} from "@/configs";

function useWebSocket(handleMessage){
    const ws=new WebSocket(WS_ADRESS)
    const init=()=>{
        bingEvent();
    }
    function bingEvent(){
        ws.addEventListener('open',handleOpen,false);
        ws.addEventListener('close',handleClose,false);
        ws.addEventListener('error',handleError,false);
        ws.addEventListener('message',handleMessage,false);
    }

    function handleOpen(e){
        console.log('WebSocket open',e);
    }
    function handleClose(e){
        console.log('WebSocket close',e);
    }
    function handleError(e){
        console.log('WebSocket error',e);
    }
    init();
    return ws;
}
export default useWebSocket;