package IR;

import MIPS.*;

public class IRcommand_sbrk extends IRcommand {
    public void MIPSme() {
        MIPS.writer.printf("\tli $v0, 9\n");
        MIPS.writer.printf("\tsyscall\n");
    }
}
