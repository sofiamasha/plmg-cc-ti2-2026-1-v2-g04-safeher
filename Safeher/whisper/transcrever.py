import whisper
import warnings

warnings.filterwarnings("ignore")

model = whisper.load_model("tiny")

resultado = model.transcribe(
    "audios/socorro.wav",
    language="pt",
    task="transcribe"
)

print(resultado["text"])