/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.util

class DefaultTimestampGenerator extends TimestampGenerator {

  override def timestamp: Long = System.currentTimeMillis
}
