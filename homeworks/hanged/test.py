
def test():
    with open('listado-general.txt', 'r') as fp:
        text = fp.read()
        words = text.split("\n")
        # for w in words: print(len(w), w)
        nums = list( map(lambda w: len(w), words))
        nums.sort()
        #print(nums)
        unrepeated = list(set(nums))
        unrepeated.sort()
        for x in unrepeated:
            print(x, nums.count(x))

def longest_word_size(words):
    arr = list( map(lambda w: len(w), words) )
    arr.sort()
    return arr[-1]

def load_dictionary():
    result = dict()
    for i in range(3): result[i] = list()
    with open('listado-general.txt', 'r') as fp:
        words = filter(lambda w: len(w) > 3, fp.read().split("\n"))
        step = int(longest_word_size(words) / 3)
        for word in words: 
            k = len(word)
            if k < step: result[0].append(word)
            elif k < 2 * step: result[1].append(word)
            else: result[2].append(word)
    return result

if __name__ == '__main__':
    store = (load_dictionary())
    for i in range(len(store)):
        print(len(store[i]))