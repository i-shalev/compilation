from pathlib import Path
from subprocess import *
import os

PRINT_TO_CONSOLE = True
TEST_RESULTS = "./FOLDER_5_OUTPUT/test_results.txt"  # if PRINT_TO_CONSOLE is false
SKIP = ["Input"]
OUTPUT_FILENAME = "SemanticStatus"
REFERENCE_JAR = "COMPILER_REF"
SHOW_DIFF = False

def main():
    total = 0
    passed = 0

    input_dir = Path("./FOLDER_4_INPUT")
    expected_output_dir = Path("./FOLDER_6_EXPECTED_OUTPUT")
    output_file = Path("./FOLDER_5_OUTPUT") / (OUTPUT_FILENAME + ".txt")
    output_file_ref = Path("./FOLDER_5_OUTPUT") / (OUTPUT_FILENAME + "_REF" + ".txt")

    spim_output = Path("./FOLDER_5_OUTPUT") / "SPIM_Result.txt"
    spim_output_ref = Path("./FOLDER_5_OUTPUT") / "SPIM_Result_REF.txt"

    if not PRINT_TO_CONSOLE:
        test_result_file = open(TEST_RESULTS, "w")

    for test in input_dir.iterdir():
        test_name = test.stem

        if test_name in SKIP:
            continue

        total += 1

        os.system(f'java -jar ./COMPILER {test} {output_file}')  # execute test on our program
        os.system(f'java -jar ./{REFERENCE_JAR} {test} {output_file_ref}')  # execute test on reference

        os.system(f'spim -f {output_file} > {spim_output}')  # execute test on our program
        os.system(f'spim -f {output_file_ref} > {spim_output_ref}')  # execute test on our program

        actual = open(spim_output).read()
        expected = open(spim_output_ref).read()

        if expected == actual:
            res = f'{test_name} - succeeded '  # with result: {expected[:-1]}'
            print(res) if PRINT_TO_CONSOLE else test_result_file.write(res)
            passed += 1
        else:
            res = f'{test_name} - failed'  # : expected {expected[:-1]} but found {actual[:-1]}'
            print(res) if PRINT_TO_CONSOLE else test_result_file.write(res)
            if SHOW_DIFF:
                print('\ndifference:\n')
                os.system(f'diff {spim_output} {spim_output_ref}')
                print('\n')

    res = f"\nSUMMARY: {passed}/{total} tests passed"
    print(res) if PRINT_TO_CONSOLE else test_result_file.write(res)

    if not PRINT_TO_CONSOLE:
        test_result_file.close()


if __name__ == '__main__':
    main()
