#!/usr/bin/env python

import random
import argparse
import string


def random_chars(amount):
    return [random.choice(string.letters) for x in xrange(amount)]


def random_integers(amount):
    return [str(random.randint(0, 1000)) for x in xrange(amount)]


def main():
    parser = argparse.ArgumentParser(description='Generate random data and store it in a file.')
    parser.add_argument('--type', '-t', default='i', choices=[
                        'i', 'c'], help="The type of data to generate ([c]hars or [i]nts).")
    parser.add_argument('--amount', '-a', default=1000, type=int, help="The number of data items to generate.")
    parser.add_argument('--output', '-o', default='out.txt', type=argparse.FileType(
        'w'), help="Where to store the generated data.")

    args = parser.parse_args()

    random = random_integers if args.type == 'i' else random_chars

    args.output.write('\n'.join(random(args.amount)))
    args.output.close()


if __name__ == '__main__':
    main()
