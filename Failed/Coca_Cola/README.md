# Coca Cola
CATEGORY - POINTS points

## Challenge 


## Solution
p64(0x20646574696d694c) + p64(0x206e6f6974696445) + p64(0x6c6f432061636f43) + p64(0x646f7250202d2061) + p64(0x4d20666f20746375) + p32(0x63697865) + p32(0x6f)              
'Limited Edition Coca Cola - Product of Mexico\x00\x00\x00'

	int cola() {
	    var_8 = *0x28;
	    rax = 0x0;
	    rcx = sign_extend_64(rand());
	    rax = rcx * 0x36f9bfb3af7b757;
	    var_58 = (rcx - ((SAR(HIQWORD(rcx * 0x36f9bfb3af7b757), 0x1b)) - (SAR(rcx, 0x3f))) * 0x2540be400) + 0x1;
	    if (*(int32_t *)something != 0x0) {
	            var_60 = 0x1337;
	            *(&var_60 + 0x10) = 0x20646574696d694c;
	            *(&var_60 + 0x18) = 0x206e6f6974696445;
	            *(&var_60 + 0x20) = 0x6c6f432061636f43;
	            *(&var_60 + 0x28) = 0x646f7250202d2061;
	            *(&var_60 + 0x30) = 0x4d20666f20746375;
	            *(int32_t *)(&var_60 + 0x38) = 0x63697865;
	            *(int16_t *)(&var_60 + 0x3c) = 0x6f;
	            var_18 = "Invalid internal error.";
	    }
	    rax = puts("Here's your randomly generated coke can!");
	    rax = 0x0;
	    rax = printf("Version: V.%lu\n", var_60);
	    rax = 0x0;
	    rax = printf("Serial Number: %lu\n", var_58);
	    rax = 0x0;
	    rax = printf("Title: %s\n", &var_60 + 0x10);
	    if (((*(int8_t *)flag & 0xff) == 0x44) && ("Invalid internal error." != 0x0)) {
	            rax = puts("Errors were found.");
	            rax = 0x0;
	            rax = printf("Error: %s\n", "Invalid internal error.");
	    }
	    rax = *0x28 ^ *0x28;
	    if (rax != 0x0) {
	            rax = __stack_chk_fail();
	    }
	    return rax;
	}

## Flag

	??