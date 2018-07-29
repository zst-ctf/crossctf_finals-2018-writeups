# Perfect
CATEGORY - POINTS points

## Challenge 


## Solution

	int sub_400b46() {
	    var_8 = *0x28;
	    rax = 0x0;
	    rax = __gmpz_init(&var_460);
	    rax = __gmpz_set_ui(&var_460, 0x0);
	    rax = __gmpz_init(&var_450);
	    rax = __gmpz_set_ui(&var_450, 0x0);
	    rax = __gmpz_init(&var_440);
	    rax = __gmpz_set_ui(&var_440, 0x0);
	    rax = __gmpz_init(&var_430);
	    rax = __gmpz_set_ui(&var_430, 0x0);
	    rax = __gmpz_init(&var_420);
	    rax = __gmpz_set_ui(&var_420, 0x2);
	    rax = __gmpz_mul_2exp(&var_420, &var_420, 0xd4, &var_420);
	    rax = 0x0;
	    rax = printf("Eschucha? ");
	    rax = 0x0;
	    rax = __isoc99_scanf("%1023s", &var_410);

	    // returns 0 if valid
	    // https://gmplib.org/manual/Assigning-Integers.html
	    if (__gmpz_set_str(&var_440, &var_410, 0xa, &var_410) != 0x0) {
	            rax = __assert_fail("flag == 0", "perfect.c", 0x20, 0x400fcf);
	    }
	    else {
	            rax = __gmpz_sub_ui(&var_440, &var_440, 0x1, &var_440);
	            if (__gmpz_set_str(&var_430, &var_410, 0xa, &var_410) != 0x0) {
	                    rax = __assert_fail("flag == 0", "perfect.c", 0x23, 0x400fcf);
	            }
	            else {
	                    while ((var_43C < 0x0) || (var_43C > 0x0)) {
	                            rax = __gmpz_mod(&var_450, &var_430, &var_440, &var_430);
	                            if ((var_44C >= 0x0) && (var_44C <= 0x0)) {
	                                    rax = __gmpz_add(&var_460, &var_460, &var_440, &var_460);
	                            }
	                            rax = __gmpz_sub_ui(&var_440, &var_440, 0x1, &var_440);
	                    }
	                    if (__gmpz_cmp(&var_430, &var_460, &var_460) == 0x0) {
	                            if (__gmpz_cmp(&var_430, &var_420, &var_420) > 0x0) {
	                                    rax = 0x0;
	                                    rax = printf("random.seed(");
	                                    rax = 0x0;
	                                    rax = __gmpz_out_str(*__bss_start, 0xa, &var_460);
	                                    rax = puts(0x400f3b);
	                                    rax = puts("k = \"\".join([chr(random.randint(0, 255)) for i in range(35)])");
	                                    rax = puts("xor(k, 754e26ccd4b1bfafb3ffbdaa748780b7f0e0c3ae9acc3c008670f0fafd34f8ffa596db)");
	                            }
	                    }
	                    rax = __gmpz_clear(&var_460);
	                    rax = __gmpz_clear(&var_430);
	                    rax = __gmpz_clear(&var_440);
	                    rax = __gmpz_clear(&var_450);
	                    rax = __gmpz_clear(&var_420);
	                    rax = 0x0;
	                    rcx = *0x28 ^ *0x28;
	                    if (rcx != 0x0) {
	                            rax = __stack_chk_fail();
	                    }
	            }
	    }
	    return rax;
	}


## Flag

	??