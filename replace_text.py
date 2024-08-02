import os

def replace_text_in_file(file_path, old_text, new_text):
    with open(file_path, 'r') as file:
        file_data = file.read()
    
    file_data = file_data.replace(old_text, new_text)
    
    with open(file_path, 'w') as file:
        file.write(file_data)

def replace_text_in_directory(directory, old_text, new_text):
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.java') or file.endswith('.xml') or file.endswith('.md') or file.endswith('.gradle'):
                file_path = os.path.join(root, file)
                replace_text_in_file(file_path, old_text, new_text)

# Replace 'timesapp' with 'shutt' in the cloned repository directory
repository_directory = '/data/data/com.termux/files/home/TimesApp'
replace_text_in_directory(repository_directory, 'timesapp', 'shutt')

print("Replacement complete!")

