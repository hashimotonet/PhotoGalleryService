# gpt-4o-mini
# $0.150 / 1M input tokens
# $0.075 / 1M input tokens
# $0.600 / 1M output tokens

import os
import sys
import openai
from openai import OpenAI

# デプロイ時に削除
openai.api_key = os.getenv("OPENAI_API_KEY")
openai.api_key = '*********'

args = sys.argv
image_url = args[1] 

if not openai.api_key:
    raise KeyError('OPENAI_API_KEY is not set')

client = OpenAI()

model_name = "gpt-4o-mini"
prompt = "この画像について解説してください"

response = client.chat.completions.create(
model=model_name,
messages=[
{
"role": "user",
"content": [
{"type": "text", "text": prompt},
{
"type": "image_url",
"image_url": {
"url": image_url
},
},
],
}
],
max_tokens=1200,
)

print(response.choices[0].message.content)
