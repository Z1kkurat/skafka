package com.evolutiongaming.skafka.consumer

import cats.effect.Sync
import com.evolutiongaming.catshelper.ToTry
import com.evolutiongaming.skafka.Converters._
import com.evolutiongaming.skafka.FromBytes
import org.apache.kafka.clients.consumer.{Consumer => ConsumerJ, KafkaConsumer}

object CreateConsumerJ {

  def apply[F[_]: Sync: ToTry, K, V](
    config: ConsumerConfig,
    fromBytesK: FromBytes[F, K],
    fromBytesV: FromBytes[F, V]
  ): F[ConsumerJ[K, V]] = {
    val deserializerK = fromBytesV.asJava
    val deserializerV = fromBytesK.asJava
    Sync[F].delay { new KafkaConsumer(config.properties, deserializerV, deserializerK) }
  }
}
