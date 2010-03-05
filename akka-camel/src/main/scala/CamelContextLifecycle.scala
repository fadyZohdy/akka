/**
 * Copyright (C) 2009-2010 Scalable Solutions AB <http://scalablesolutions.se>
 */

package se.scalablesolutions.akka.camel

import org.apache.camel.{ProducerTemplate, CamelContext}
import org.apache.camel.impl.DefaultCamelContext

import se.scalablesolutions.akka.util.Logging

/**
 * Defines the lifecycle of a CamelContext. Allowed state transitions are
 * init -> start -> stop -> init -> ... etc. 
 *
 * @author Martin Krasser
 */
trait CamelContextLifecycle extends Logging {
  // TODO: enforce correct state transitions
  // valid: init -> start -> stop -> init ...

  private var _context: CamelContext = _
  private var _template: ProducerTemplate = _

  private var _initialized = false
  private var _started = false

  /**
   * Returns the managed CamelContext.
   */
  protected def context: CamelContext = _context

  /**
   * Returns the managed ProducerTemplate.
   */
  protected def template: ProducerTemplate = _template

  /**
   * Sets the managed CamelContext.
   */
  protected def context_= (context: CamelContext) { _context = context }

  /**
   * Sets the managed ProducerTemplate.
   */
  protected def template_= (template: ProducerTemplate) { _template = template }

  def initialized = _initialized
  def started = _started

  /**
   * Starts the CamelContext and ProducerTemplate.
   */
  def start() {
    context.start
    template.start
    _started = true
    log.info("Camel context started")
  }

  /**
   * Stops the CamelContext and ProducerTemplate.
   */
  def stop() {
    template.stop
    context.stop
    _initialized = false
    _started = false
    log.info("Camel context stopped")
  }

  /**
   * Initializes this lifecycle object with the a DefaultCamelContext.
   */
  def init() {
    init(new DefaultCamelContext)
  }

  /**
   * Initializes this lifecycle object with the given CamelContext.
   */
  def init(context: CamelContext) {
    this.context = context
    this.template = context.createProducerTemplate
    _initialized = true
    log.info("Camel context initialized")
  }
}

/**
 * Makes a global CamelContext and ProducerTemplate accessible to applications. The lifecycle
 * of these objects is managed by se.scalablesolutions.akka.camel.service.CamelService.
 */
object CamelContextManager extends CamelContextLifecycle {
  override def context: CamelContext = super.context
  override def template: ProducerTemplate = super.template
}