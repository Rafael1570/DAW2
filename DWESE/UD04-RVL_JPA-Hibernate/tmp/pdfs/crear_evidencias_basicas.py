from pathlib import Path
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from PIL import Image
from pypdf import PdfReader

root=Path('/home/usuario-m/Documentos/DAW2/DWESE/UD04-RVL_JPA-Hibernate')
output=root/'output/pdf/Evidencias_UD04_Rafael_Vega_Librero.pdf'
images=sorted((root/'evidencias').glob('*.jpg'))
assert len(images)==8
w,h=A4
c=canvas.Canvas(str(output),pagesize=A4,pageCompression=1)
c.setTitle('Evidencias UD04 - Rafael Vega Librero')
c.setAuthor('Rafael Vega Librero')
for source in images:
 with Image.open(source) as image:iw,ih=image.size
 scale=min((w-36)/iw,(h-36)/ih)
 sw,sh=iw*scale,ih*scale
 c.drawImage(str(source),(w-sw)/2,h-18-sh,width=sw,height=sh)
 c.showPage()
c.save()
pdf=PdfReader(output)
assert len(pdf.pages)==8
for page in pdf.pages:
 assert float(page.mediabox.height)>float(page.mediabox.width)
 assert len(page.images)==1
 assert not page.extract_text().strip()
print('Verificado: 8 páginas A4 verticales, una captura por página y ningún texto añadido.')
