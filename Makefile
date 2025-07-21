TOPLEVEL = StackModule
MODULE = stack_tb
SIM ?= verilator
TOPLEVEL_LANG ?= verilog
VERILOG_SOURCES += $(wildcard $(CURDIR)/out/stack.SVGen/*.v $(CURDIR)/out/stack.SVGen/*.sv)
EXTRA_ARGS += --trace --trace-structs
include $(shell cocotb-config --makefiles)/Makefile.sim
