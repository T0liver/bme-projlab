import os

def compare_files(expected_path, output_path):
    """Compare the contents of two files."""
    try:
        with open(expected_path, 'r') as f_expected, open(output_path, 'r') as f_output:
            expected_content = f_expected.read()
            output_content = f_output.read()
            return expected_content == output_content
    except FileNotFoundError as e:
        print(f"Missing file: {e}")
        return False

def scan_and_compare(root_dir):
    """Scan all subdirectories and compare expected.txt and output.txt."""
    for dirpath, dirnames, filenames in os.walk(root_dir):
        if 'expected.txt' in filenames and 'output.txt' in filenames:
            expected_path = os.path.join(dirpath, 'expected.txt')
            output_path = os.path.join(dirpath, 'output.txt')
            result = compare_files(expected_path, output_path)
            relative_path = os.path.relpath(dirpath, root_dir)
            if result:
                print(f"[MATCH] {relative_path}")
            else:
                print(f"[MISMATCH] {relative_path}")
        else:
            if 'expected.txt' in filenames or 'output.txt' in filenames:
                print(f"[WARNING] Incomplete pair in {os.path.relpath(dirpath, root_dir)}")

if __name__ == "__main__":
    script_dir = os.path.dirname(os.path.abspath(__file__))
    scan_and_compare(script_dir)
