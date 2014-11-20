package jms.rabbitmq.receiver;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class DurableReceiverRouting {

	//private static final String ROUTING_KEY = "DisneyRoutingKey1";

	private static final String TASK_QUEUE_NAME = "DurableTestQueue";

	public static void main(String[] argv) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.74.230.148");
		factory.setUsername("disney");
		factory.setPassword("disney");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		boolean durable = false;
		
		channel.queueDeclare(TASK_QUEUE_NAME, durable=true, false, false, null);
		System.out.println(" [*] Waiting for messages.");

		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}         
	}
}