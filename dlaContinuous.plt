set terminal pdfcairo size 20cm,20cm font "Times-New-Roman" fontscale 1.
set output "DLAContinuous.pdf"
set size square
set xrange[100:900]
set yrange[100:900]
unset xtics
unset ytics
plot "DLAContinuous-output.txt" with points pt 7 ps .1 lc rgb 'red' notitle
