#!/usr/bin/Rscript --vanilla

library(reshape)
args <- commandArgs(trailingOnly = TRUE)
dane<-read.csv2(args[1])
dane<-subset(dane,dane$result!="")
dane<-dane[c(1:3)]
won1<-rename(subset(dane,dane$result=="PLAYER1")[c(1)], c(player1="player"))
won2<-rename(subset(dane,dane$result=="PLAYER2")[c(2)], c(player2="player"))
dane<-rbind(won1, won2)
write.csv2(table(dane),args[2],row.names=FALSE)
