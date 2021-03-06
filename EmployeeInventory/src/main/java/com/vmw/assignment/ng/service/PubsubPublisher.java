package com.vmw.assignment.ng.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

@Service
public class PubsubPublisher {

	@Value("${spring.cloud.gcp.project-id}")
	private String projectId;


	public void publish(String topicName, String message) throws Exception {
		Publisher publisher = null;
		try {
			// Create a publisher instance with default settings bound to the topic
			publisher = getConfiguredPublisher(topicName);

			PubsubMessage pubsubMessage = getPubsubMessageFromString(message);

			ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
			String messageId = messageIdFuture.get();
			System.out.println("Published message ID: " + messageId);
		} finally {
			if (publisher != null) {
				// When finished with the publisher, shutdown to free up resources.
				publisher.shutdown();
				// publisher.awaitTermination(1, TimeUnit.MINUTES);
			}
		}
	}

	private PubsubMessage getPubsubMessageFromString(String message) {
		ByteString data = ByteString.copyFromUtf8(message);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
		return pubsubMessage;
	}

	private Publisher getConfiguredPublisher(String topicName) throws IOException {
		ProjectTopicName projectTopicName = ProjectTopicName.of(projectId, topicName);
		return Publisher.newBuilder(projectTopicName).build();
	}

}
