from pathlib import Path
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4, landscape
from reportlab.lib.colors import HexColor
from reportlab.lib.utils import ImageReader
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from PIL import Image
from pypdf import PdfReader

root=Path('/home/usuario-m/Documentos/DAW2/DWESE/UD04-RVL_JPA-Hibernate')
output=root/'output/pdf/Evidencias_UD04_Rafael_Vega_Librero.pdf'
font=Path('/usr/share/fonts/truetype/dejavu')
pdfmetrics.registerFont(TTFont('DejaVu',str(font/'DejaVuSans.ttf')))
pdfmetrics.registerFont(TTFont('DejaVu-Bold',str(font/'DejaVuSans-Bold.ttf')))
entries=[
('01-regiones-paginacion.jpg','Listado de comunidades autónomas','Primera página ordenada por nombre: 18 registros distribuidos en cuatro páginas.'),
('02-regiones-segunda-pagina.jpg','Navegación entre páginas','La segunda página muestra otros registros y conserva la ordenación ascendente.'),
('03-regiones-orden-descendente.jpg','Ordenación descendente','El encabezado Nombre indica el orden descendente; País Vasco aparece en primer lugar.'),
('04-provincias-paginacion.jpg','Listado de provincias','Se muestran las provincias con su comunidad autónoma y la paginación de 52 registros.'),
('05-detalle-region-provincias.jpg','Detalle de una comunidad autónoma','El detalle de Andalucía muestra sus ocho provincias y enlaces para consultar cada una.'),
('06-detalle-provincia-region.jpg','Detalle de una provincia','El detalle de Almería muestra sus datos y un enlace a la comunidad de Andalucía.'),
('07-formulario-edicion-provincia.jpg','Formulario de edición','El formulario carga código, nombre y comunidad de Almería, preparados para su edición.'),
('08-internacionalizacion-ingles.jpg','Internacionalización','La interfaz en inglés traduce la navegación, los encabezados, las acciones y la paginación.')]
w,h=landscape(A4)
c=canvas.Canvas(str(output),pagesize=(w,h),pageCompression=1)
c.setTitle('UD04 - Evidencias de funcionamiento - Rafael Vega Librero')
c.setAuthor('Rafael Vega Librero')
c.setSubject('Ticket Logger: paginación, ordenación, relaciones, edición e internacionalización')
for i,(filename,title,description) in enumerate(entries,1):
 c.setFillColor(HexColor('#151515'));c.rect(0,h-31,w,31,fill=1,stroke=0)
 c.setFillColor(HexColor('#ffffff'));c.setFont('DejaVu-Bold',9)
 c.drawString(30,h-20,'TICKET LOGGER  /  UD04')
 c.setFont('DejaVu',8);c.drawRightString(w-30,h-20,'DESARROLLO WEB EN ENTORNO SERVIDOR · DAW2')
 c.setFillColor(HexColor('#161616'));c.setFont('DejaVu-Bold',17)
 c.drawString(30,h-59,f'{i:02d}. {title}')
 c.setFillColor(HexColor('#555555'));c.setFont('DejaVu',9)
 c.drawString(30,h-77,'Rafael Vega Librero  |  6 de noviembre de 2026')
 c.setFillColor(HexColor('#333333'));c.setFont('DejaVu',9)
 c.drawString(30,h-96,description)
 source=root/'evidencias'/filename
 with Image.open(source) as im:iw,ih=im.size
 # Keep the complete screenshot, including the application's author footer.
 left,bottom=30,51; available_w=w-60;available_h=h-164
 scale=min(available_w/iw,available_h/ih)
 sw,sh=iw*scale,ih*scale
 x=left+(available_w-sw)/2;y=bottom+(available_h-sh)/2
 c.setStrokeColor(HexColor('#dddddd'));c.setLineWidth(.5)
 c.rect(x-.5,y-.5,sw+1,sh+1,stroke=1,fill=0)
 c.drawImage(str(source),x,y,width=sw,height=sh)
 c.setStrokeColor(HexColor('#dddddd'));c.line(30,35,w-30,35)
 c.setFillColor(HexColor('#666666'));c.setFont('DejaVu',7)
 c.drawString(30,22,'Capturas reales de la aplicación local · Perfil demo (H2) · Nombre visible en cada captura')
 c.drawRightString(w-30,22,f'{i} / {len(entries)}')
 c.showPage()
c.save()
reader=PdfReader(output)
assert len(reader.pages)==8
for page in reader.pages:
 assert 'Rafael Vega Librero' in page.extract_text()
 assert len(page.images)==1
print(output)
print('Verificado: 8 páginas, 8 capturas completas y nombre en todas las páginas.')
