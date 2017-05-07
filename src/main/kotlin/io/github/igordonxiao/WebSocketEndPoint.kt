package io.github.igordonxiao

import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.*
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value = "/ws")
@Component
class WebSocketEndPoint {
    companion object {
        private val webSocketSet = CopyOnWriteArraySet<WebSocketEndPoint>()

    }

    private var session: Session? = null

    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    fun onOpen(session: Session) {
        webSocketSet.add(this)
        this.session = session
        session.getBasicRemote().sendText("0")

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    fun onClose() {
        webSocketSet.remove(this)
    }

    /**
     * 收到客户端消息后调用的方法

     * @param message 客户端发送过来的消息
     */
    @OnMessage
    fun onMessage(message: String, session: Session) {
        println("来自客户端的消息:" + message)
        webSocketSet.forEach {
            it.session?.getBasicRemote()?.sendText(message)
        }
    }

    /**
     * 发生错误调用
     */
    @OnError
    fun onError(session: Session, error: Throwable) {
        println("发生错误")
        error.printStackTrace()
    }
}
