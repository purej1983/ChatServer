package com.thomas

import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testWebSocketsSingleClient() = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/chat") {
            val greetingText = (incoming.receive() as? Frame.Text)?.readText() ?: ""
            assertEquals("You are connected!", greetingText)

            val greetingCountText = (incoming.receive() as? Frame.Text)?.readText() ?: ""
            assertEquals("You are connected! There are 1 users here.", greetingCountText)


            send(Frame.Text("JetBrains"))
            val responseText = (incoming.receive() as Frame.Text).readText()
            assertEquals("[user0]: JetBrains", responseText)
        }
    }

    @Test
    fun testWebSocketsMoreClient() = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        val client2 = createClient {
            install(WebSockets)
        }


        val session1 = client.webSocketSession("/chat")
        val session2 = client2.webSocketSession("/chat")
        delay(1000)
        val client1GreetingText = (session1.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("You are connected!", client1GreetingText)

        val client1GreetingCountText = (session1.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("You are connected! There are 1 users here.", client1GreetingCountText)


        val client2GreetingText = (session2.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("You are connected!", client2GreetingText)

        val client2GreetingCountText = (session2.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("You are connected! There are 2 users here.", client2GreetingCountText)

        session1.send(Frame.Text("Apple"))
        val client1Message1 = (session1.incoming.receive() as? Frame.Text)?.readText() ?: ""
        val client2Message1 = (session2.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("[user0]: Apple", client1Message1)
        assertEquals("[user0]: Apple", client2Message1)

        session2.send(Frame.Text("Orange"))
        val client1Message2 = (session1.incoming.receive() as? Frame.Text)?.readText() ?: ""
        val client2Message2 = (session2.incoming.receive() as? Frame.Text)?.readText() ?: ""
        assertEquals("[user1]: Orange", client1Message2)
        assertEquals("[user1]: Orange", client2Message2)

        session1.close()
        session2.close()

    }
}
