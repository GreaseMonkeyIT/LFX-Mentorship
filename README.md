LFX Mentorship Fall 2025 Coding Challenge
=======================

## Overview

The aim of this coding challenge is to create a hardware stack module which has the ability to push, pop and peek according to a given instruction. In order to attempt the coding challenge, fork this branch of the repository and attempt the challenge. Upon completion, create a PDF file consisting of the link to your fork, the code (with the mention of file name) you have written and the output you recieved.

## Stack Module

The `StackModule` is the name of the hardware stack to be implemented. This main module must be written in `src/main/scala/stack/StackModule.scala`. The `StackModule` must be parameterized with the following parameters.
- **dataWidth:** The bitwidth of a single element to be stored in the stack.
- **len:** The length of the stack.

### Stack Module Interface

#### Inputs

- **io.in:** A `dataWidth` wide input port. Used to input instructions.

#### Outputs

- **io.out:** A `dataWidth` wide input port. Used to output values from the stack.
- **io.underflow:** A 1-bit port used to indicate underflow flag of the `pop` and `peek` instructions.
- **io.overflow:** A 1-bit port used to indicate overflow flag of the `push` instructions.
- **io.isEmpty:** A 1-bit port used to indicate that the stack is empty.
- **io.isFull:** A 1-bit port used to indicate that the stack is full.
- **io.popped:** A 1-bit port used to indicate that the successful popping of a value from the stack.
- **io.peeked:** A 1-bit port used to indicate that the successful peeking of a value from the stack.

## Instructions

Based on the following instructions and their encodings, the `StackModule` will perform operations.

| instruction | instruction[31:7] (imm[24:0]) | instruction[6:0] (opcode) |
| - | - | - |
| push | imm[24:0] | 0100111 |
| pop | 0000000000000000000000000 | 1000011 |
| peek | 0000000000000000000000000 | 1000000 |

- **push:** The push instruction inserts the `dataWidth`-bit zero-extended immediate to the stack. The `io.isFull` output is asserted when the stack becomes full. If the stack is already full, `io.overflow` will be asserted.
- **pop:** The pop instruction removes the `dataWidth`-bit value from the top of the stack and sends it to `io.out` while also asserting `io.popped`. `io.isEmpty` is asserted if the stack becomes empty. If the stack is already empty, `io.underflow` will be asserted and the value 0 will be read..
- **peek:** The peek instruction reads the `dataWidth`-bit value from the top of the stack and sends it to `io.out` while also asserting `io.peeked`. If the stack is already empty, `io.underflow` will be asserted and the value 0 will be read.

## Run Tests

In order to run tests, you need to have `cocotb` and `bitstring` installed.
```sh
pip install 'cocotb~=1.9'
pip install bitstring
```

To simulate and run the tests on your DUT, run:
```sh
python3 run_tests.py
```
