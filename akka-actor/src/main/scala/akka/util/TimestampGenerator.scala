/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.util

/**
 * this trait is to allow the user to use his own custom timestamp generator
 */
trait TimestampGenerator {

  def timestamp: Long
}
