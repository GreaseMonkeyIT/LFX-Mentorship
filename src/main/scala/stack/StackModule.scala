package stack

import chisel3._
import chisel3.util._
import scala.math.min

class StackModule(val dataWidth: Int, val len: Int) extends Module {
  val io = IO(new Bundle {
    val in         = Input(UInt(32.W))
    val out        = Output(UInt(dataWidth.W))
    val underflow  = Output(Bool())
    val overflow   = Output(Bool())
    val isEmpty    = Output(Bool())
    val isFull     = Output(Bool())
    val popped     = Output(Bool())
    val peeked     = Output(Bool())
  })

  val opcode    = RegNext(io.in(6, 0), 0.U)
  val immediate = RegNext(io.in(31, 7), 0.U)

  // ===STATE===
  val sp    = RegInit(0.U(log2Ceil(len + 1).W))
  val stack = RegInit(VecInit(Seq.fill(len)(0.U(dataWidth.W))))

  // ===OUTPUT REGS===
  val outReg       = RegInit(0.U(dataWidth.W))
  val underflowReg = RegInit(false.B)
  val overflowReg  = RegInit(false.B)
  val poppedReg    = RegInit(false.B)
  val peekedReg    = RegInit(false.B)

  // ===FLAG CLEAR EACH CYCLE===
  outReg       := 0.U
  underflowReg := false.B
  overflowReg  := false.B
  poppedReg    := false.B
  peekedReg    := false.B

  // ===EXECUTE BASED ON REGISTERED OPCODE===
  switch(opcode) {
    is("b0100111".U) { // PUSH
      when(sp === len.U) {
        overflowReg := true.B
      }.otherwise {
        stack(sp) := immediate(min(dataWidth, 25) - 1, 0)
        sp := sp + 1.U
      }
    }
    is("b1000011".U) { // POP
      when(sp === 0.U) {
        underflowReg := true.B
      }.otherwise {
        sp := sp - 1.U
        outReg       := stack(sp - 1.U)
        poppedReg    := true.B
      }
    }
    is("b1000000".U) { // PEEK
      when(sp === 0.U) {
        underflowReg := true.B
      }.otherwise {
        outReg    := stack(sp - 1.U)
        peekedReg := true.B
      }
    }
  }

  // ===OUTPUT DRIVER===
  io.out       := outReg
  io.underflow := underflowReg
  io.overflow  := overflowReg
  io.popped    := poppedReg
  io.peeked    := peekedReg
  io.isEmpty   := sp === 0.U
  io.isFull    := sp === len.U
}
