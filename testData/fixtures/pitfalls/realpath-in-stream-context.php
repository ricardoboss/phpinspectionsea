<?php

    /* the main case */
    <warning descr="[EA] 'dirname(__DIR__) . '/'' should be used instead (due to how realpath handles streams).">realpath(__DIR__ . '/../')</warning>;
    <warning descr="[EA] 'dirname(dirname(dirname(__DIR__))) . '/'' should be used instead (due to how realpath handles streams).">realpath(__DIR__ . '/../../../')</warning>;
    <warning descr="[EA] 'realpath(...)' works differently in a stream context (e.g., for phar://...). Consider using 'dirname(...)' instead.">realpath(__DIR__ . '/..' . '/..')</warning>;

    /* similar cases, when the issues will popup */
    include <warning descr="[EA] ''/whatever/file.php'' should be used instead (due to how realpath handles streams).">realpath('/whatever/file.php')</warning>;
    include_once <warning descr="[EA] ''/whatever/file.php'' should be used instead (due to how realpath handles streams).">realpath('/whatever/file.php')</warning>;
    require <warning descr="[EA] ''/whatever/file.php'' should be used instead (due to how realpath handles streams).">realpath('/whatever/file.php')</warning>;
    require_once <warning descr="[EA] ''/whatever/file.php'' should be used instead (due to how realpath handles streams).">realpath('/whatever/file.php')</warning>;
    require_once <warning descr="[EA] 'realpath(...)' works differently in a stream context (e.g., for phar://...). Consider using 'dirname(...)' instead.">realpath($x)</warning>;

    /* the same, but wrapped with () */
    include (<warning descr="[EA] ''/whatever/file.php'' should be used instead (due to how realpath handles streams).">realpath('/whatever/file.php')</warning>);


    /* false-positives */
    echo(realpath(__DIR__ . '/'));