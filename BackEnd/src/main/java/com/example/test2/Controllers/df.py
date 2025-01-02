import sys
import requests
from bs4 import BeautifulSoup
import io

def get_definition(query):
    search_url = f"https://www.google.com/search?q={query.replace(' ', '+')}+definition"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"}
    response = requests.get(search_url, headers=headers)

    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'html.parser')

        definition_element = soup.find('div', class_='BNeawe s3v9rd AP7Wnd')
        if definition_element:
            definition = definition_element.text
            return definition
        else:
            dictionary_element = soup.find('div', class_='BNeawe tAd8D AP7Wnd')
            if dictionary_element:
                definition = dictionary_element.text
                return definition
            else:
                return "Definition not found."
    else:
        return "Failed to retrieve definition."

if __name__ == "__main__":
    if len(sys.argv) > 1:
        search_query = sys.argv[1]

        # Redirect standard output to a byte stream
        sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

        definition = get_definition(search_query)
        print("Definition:", definition)
