package stack

import chisel3.stage.ChiselStage
import chisel3.stage.ChiselGeneratorAnnotation

object SVGen {
  def main(args: Array[String]): Unit = {
    require(args.length == 2, "Usage: runMain stack.SVGen <dataWidth> <stackDepth>")
    val dataWidth = args(0).toInt
    val length    = args(1).toInt

    (new ChiselStage).execute(
      Array("--target-dir", "out/stack.SVGen"),
      Seq(ChiselGeneratorAnnotation(() => new StackModule(dataWidth, length)))
    )
  }
}
