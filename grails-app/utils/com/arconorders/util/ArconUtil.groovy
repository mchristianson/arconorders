package com.arconorders.util

class ArconUtil {
    def static convertMessageToString(List messageList, Boolean isError) {
        messageList.flatten().collect {
            """
                <div class="msg ${isError?'msg-error':'msg-info'}"><p>${it}</p></div>
            """
        }.join()
    }
}
