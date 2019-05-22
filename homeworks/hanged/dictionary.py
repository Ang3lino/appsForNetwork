
import pdb 

def longest_word_size(words):
    arr = list( map(lambda w: len(w), words) )
    arr.sort()
    return arr[-1]

def load():
    result = dict()
    for i in range(3): result[i] = list()
    with open('listado-general.txt', 'r') as fp:
        words = tuple( filter(lambda w: len(w) > 3, fp.read().split("\n")) ) # filter object is not iterable
        step = int(longest_word_size(words) / 3)
        for word in words: 
            k = len(word)
            if k < step: result[0].append(word)
            elif k < 2 * step: result[1].append(word)
            else: result[2].append(word)
    return result