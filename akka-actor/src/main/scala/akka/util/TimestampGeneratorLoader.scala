/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.util

import akka.ConfigurationException
import akka.actor.{ ExtendedActorSystem, ActorSystem }

private[akka] object TimestampGeneratorLoader {

  /**
   * Loads and instantiates a given [[TimestampGenerator]] implementation.
   * The class to be loaded must have an [[akka.event.EventStream]] parameter.
   * Will throw ConfigurationException if the implementation cannot be loaded.
   *
   * @param fqcn Fully qualified class name of the implementation to be loaded.
   * @param system ActorSystem to be used for loading the implementation
   * @return A configured instance of the given [[TimestampGenerator]] implementation
   */
  def load(fqcn: String, system: ActorSystem): TimestampGenerator =
    system.asInstanceOf[ExtendedActorSystem].dynamicAccess.createInstanceFor[TimestampGenerator](
      fqcn, List.empty[(Class[_], AnyRef)]
    ).recover({
      case e ⇒
        throw new ConfigurationException(
          s"couldn't create custom timestamp generator $fqcn due to ${e.toString}", e)
    }).get
}
