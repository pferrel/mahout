package org.apache.mahout.sparkbindings.test

import org.scalatest.Suite
import org.apache.spark.SparkConf
import org.apache.mahout.sparkbindings._
import org.apache.mahout.test.MahoutSuite
import org.apache.mahout.math.drm.DistributedContext

trait MahoutLocalContext extends MahoutSuite with LoggerConfiguration {
  this: Suite =>

  protected implicit var mahoutCtx: DistributedContext = _
  protected var masterUrl = null.asInstanceOf[String]

  override protected def beforeEach() {
    super.beforeEach()

    masterUrl = "local[2]"
    mahoutCtx = mahoutSparkContext(masterUrl = this.masterUrl,
      appName = "MahoutLocalContext",
      // Do not run MAHOUT_HOME jars in unit tests.
      addMahoutJars = false,
      sparkConf = new SparkConf()
          .set("spark.kryoserializer.buffer.mb", "15")
          .set("spark.akka.frameSize", "30")
    )
  }

  override protected def afterEach() {
    if (mahoutCtx != null) {
      try {
        mahoutCtx.close()
      } finally {
        mahoutCtx = null
      }
    }
    super.afterEach()
  }
}
