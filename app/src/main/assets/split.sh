OLDIFS=$IFS
IFS='
'
typeset -a file
file=($(cat wordlist.txt))
for i in "${file[@]}"; do
    echo $i >> ${i:0:3}.txt
done
IFS=$OLDIFS